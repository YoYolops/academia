mod bloom_filter;
mod strategies;
mod hasher;
mod testers;
mod helpers;

use bloom_filter::BloomFilter;
use strategies::{
    strategy_md5_sha2,
    strategy_djb2_sdbm,
    strategy_xxhash
};

// Used in experiment 1
const EXP1_K_ROUNDS: usize = 4;
const EXP1_BIT_ARRAY_SIZE: usize = 20;

// Used in experiment 2
const EXP2_K_ROUNDS_1: usize = 8;
const EXP2_K_ROUNDS_2: usize = 48;
const EXP2_BIT_ARRAY_SIZE: usize = 128;


fn main() -> std::io::Result<()> {
    helpers::create_base_plot_script()?;

    {// Creating scope to drop all variables at the end. 
        // #################### EXPERIMENT 1: ####################
        // Creating one filter for each strategy
        let mut filter_md5_sha2: BloomFilter = BloomFilter::new(
            EXP1_K_ROUNDS,
            EXP1_BIT_ARRAY_SIZE,
            strategy_md5_sha2
        );
    
        let mut filter_djb2_sdbm = BloomFilter::new(
            EXP1_K_ROUNDS,
            EXP1_BIT_ARRAY_SIZE,
            strategy_djb2_sdbm
        );
    
        let mut filter_xxhash = BloomFilter::new(
            EXP1_K_ROUNDS,
            EXP1_BIT_ARRAY_SIZE,
            strategy_xxhash
        );
    
        let consistency_result_md5_sha2 = testers::test_filter_consistency(&mut filter_md5_sha2);
        let consistency_result_djb2_sdbm = testers::test_filter_consistency(&mut filter_djb2_sdbm);
        let consistency_result_xxhash = testers::test_filter_consistency(&mut filter_xxhash);

        let python_text: String = 
            String::from("consistency_result_md5_sha2 = ") + &format!("{:?}", consistency_result_md5_sha2) + "\n" + 
            &String::from("consistency_result_djb2_sdbm = ") + &format!("{:?}", consistency_result_djb2_sdbm) + "\n" + 
            &String::from("consistency_result_xxhash = ") + &format!("{:?}", consistency_result_xxhash);
        
        helpers::prepend_to_file_text("plot_script.py", &python_text)?;
    }   

    {
        // #################### EXPERIMENT 2: ####################
        let mut filter_md5_sha2_k1: BloomFilter = BloomFilter::new(
            EXP2_K_ROUNDS_1,
            EXP2_BIT_ARRAY_SIZE,
            strategy_md5_sha2
        );
        let mut filter_md5_sha2_k2: BloomFilter = BloomFilter::new(
            EXP2_K_ROUNDS_2,
            EXP2_BIT_ARRAY_SIZE,
            strategy_md5_sha2
        );
    
        let mut filter_djb2_sdbm_k1 = BloomFilter::new(
            EXP2_K_ROUNDS_1,
            EXP2_BIT_ARRAY_SIZE,
            strategy_djb2_sdbm
        );
        let mut filter_djb2_sdbm_k2 = BloomFilter::new(
            EXP2_K_ROUNDS_2,
            EXP2_BIT_ARRAY_SIZE,
            strategy_djb2_sdbm
        );
    
        let mut filter_xxhash_k1 = BloomFilter::new(
            EXP2_K_ROUNDS_1,
            EXP2_BIT_ARRAY_SIZE,
            strategy_xxhash
        );
        let mut filter_xxhash_k2 = BloomFilter::new(
            EXP2_K_ROUNDS_2,
            EXP2_BIT_ARRAY_SIZE,
            strategy_xxhash
        );
        let time_result_md5_sha2_k1 = testers::test_filter_time(&mut filter_md5_sha2_k1);
        let time_result_md5_sha2_k2 = testers::test_filter_time(&mut filter_md5_sha2_k2);
        let time_result_djb2_sdbm_k1 = testers::test_filter_time(&mut filter_djb2_sdbm_k1);
        let time_result_djb2_sdbm_k2 = testers::test_filter_time(&mut filter_djb2_sdbm_k2);
        let time_result_xxhash_k1 = testers::test_filter_time(&mut filter_xxhash_k1);
        let time_result_xxhash_k2 = testers::test_filter_time(&mut filter_xxhash_k2);

        let python_text: String = 
            String::from("time_result_md5_sha2_k1 = ") + &format!("{:?}", time_result_md5_sha2_k1) + "\n" + 
            &String::from("time_result_md5_sha2_k2 = ") + &format!("{:?}", time_result_md5_sha2_k2) + "\n" + 
            &String::from("time_result_djb2_sdbm_k1 = ") + &format!("{:?}", time_result_djb2_sdbm_k1) + "\n" +
            &String::from("time_result_djb2_sdbm_k2 = ") + &format!("{:?}", time_result_djb2_sdbm_k2) + "\n" + 
            &String::from("time_result_xxhash_k1 = ") + &format!("{:?}", time_result_xxhash_k1) + "\n" + 
            &String::from("time_result_xxhash_k2 = ") + &format!("{:?}", time_result_xxhash_k2);
        
        helpers::prepend_to_file_text("plot_script.py", &python_text)?;
    }

    let python_plot_script_execution = std::process::Command::new("python3")
        .arg("./plot_script.py")
        .status()
        .expect("Panicked while trying to execute python plot script");

    if python_plot_script_execution.success() {
        println!("The experiments plots were generated and stored in /plots/*.png")
    } else {
        println!("Failed to generate plots due to python execution error :(")
    }
    
    Ok(())
}
