use crate::hasher::{
    hash_sha512_as_int,
    hash_md5_as_int,
    hash_sdbm_as_int,
    hash_djb2_as_int,
    hash_xxhash64_as_int
};

pub fn strategy_md5_sha2(item: &str, bit_array_size: usize, k_rounds: usize) -> Vec<usize> {
    // Returns a Vector with all the bits that should be flipped to 1
    let mut indexes_to_flip: Vec<usize> = Vec::new();

    for i in 0..k_rounds as u128 {
        let h1 = hash_sha512_as_int(item);
        let h2 = hash_md5_as_int(item);
        let combined = (h1 + i * h2) % (bit_array_size as u128);
        indexes_to_flip.push(combined as usize);
    }

    indexes_to_flip
    // In this function we could sacrifice legibility to prevent panicking on integer overflow by calculating
    // {let combined} variable using wrapping_add() and wrapping_mul() as follows:
    //     let combined = h1.wrapping_add(i.wrapping_mul(h2)) % (bit_array_size as u128);
    // ps. LLM suggestion
}

pub fn strategy_djb2_sdbm(item: &str, bit_array_size: usize, k_rounds: usize) -> Vec<usize> {
    let mut indexes_to_flip: Vec<usize> = Vec::new();

    for i in 0..k_rounds as u128 {
        let h1 = hash_sdbm_as_int(item);
        let h2 = hash_djb2_as_int(item);
        let combined = (h1 + i * h2) % (bit_array_size as u128);
        indexes_to_flip.push(combined as usize);
    }

    indexes_to_flip
}

pub fn strategy_xxhash(item: &str, bit_array_size: usize, k_rounds: usize) -> Vec<usize> {
    let mut indexes_to_flip: Vec<usize> = Vec::new();

    for i in 0..k_rounds as u128 {
        let h1 = hash_xxhash64_as_int(item);
        let h2 = h1;
        let combined = (h1 + i * h2) % (bit_array_size as u128);
        indexes_to_flip.push(combined as usize);
    }

    indexes_to_flip
}