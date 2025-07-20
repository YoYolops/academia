use xxhash_rust::xxh3::xxh3_64;
use sha2::{Sha512, Digest};

pub fn hash_sha512_as_int(text: &str) -> u128 {
    let result = Sha512::digest(text.as_bytes());
    let first_16_bytes = &result[0..16];
    u128::from_be_bytes(first_16_bytes.try_into().unwrap()) // If achitecture is not be, it will still be deterministic, so no issues here.
}

pub fn hash_md5_as_int(text: &str) -> u128 {
    let result = md5::compute(text);
    let first_16_bytes = &result[0..16]; // According to chat gpt, md5 already returns 16 bytes so this is redundant, but i will keep it because the code is mine
    u128::from_be_bytes(first_16_bytes.try_into().unwrap())
}

pub fn hash_djb2_as_int(text: &str) -> u128 {
    // This one was entirely made by chat-gpt
    let mut hash: u128 = 5381;
    for byte in text.bytes() {
        // He actually teached me something about rust:
        // The following line prevents rust from panicking when integer overflows.
        // Since our program needs overflow to run correctly, we needed to add the flag --release: `cargo run --release`
        hash = ((hash << 5).wrapping_add(hash)).wrapping_add(byte as u128);
    }
    hash
}

pub fn hash_sdbm_as_int(text: &str) -> u128 {
    // Another one entirely made by GPTs. Note, yet again, the usage of wrapping_mul()
    let mut hash: u128 = 0;
    for byte in text.bytes() {
        hash = byte as u128 + hash.wrapping_mul(65599);
    }
    hash
}

pub fn hash_xxhash64_as_int(text: &str) -> u128 {
    xxh3_64(text.as_bytes()) as u128
}