use crate::bloom_filter::BloomFilter;
use std::time::Instant;


pub fn build_input_vector(size: usize) -> Vec<usize> {
    let mut input_vector: Vec<usize> = Vec::new();

    for i in 0..size {
        input_vector.push(i);
    }

    input_vector
}

pub fn test_filter_consistency(filter: &mut BloomFilter) -> [(usize, usize, usize, usize); 1000] {
    // statistical tuple: (num_inserted_items; num_collision_til_now; num_false_positives, filter_occupation)
    let mut statistics= [(0, 0, 0, 0); 1000];
    let input_vector: Vec<usize> = build_input_vector(1000);

    for test_round in 0..input_vector.len() {
        // Adds one item and then...
        filter.add(&input_vector[test_round].to_string());

        // Makes one thousand searches with un-inserted elements to count false positives
        let mut false_positives_count: usize = 0;
        for non_contained_item in 1000..2000 {
            if filter.contains(&non_contained_item.to_string()) {
                false_positives_count += 1;
            }
        }

        statistics[test_round] = (
            filter.insertions_amount(),
            filter.collisions_amount(),
            false_positives_count,
            filter.occupancy()
        );
    }
    statistics
}

pub fn test_filter_time(filter: &mut BloomFilter) -> [(usize, u128, u128, u128); 3] {
    // statistical tuple: (inserted_items_amount, total_insertion_time, average_insertion_time, time_for_thousand_searches)
    const ROUNDS: [usize; 3] = [100, 1000, 5000];
    let mut statistics: [(usize, u128, u128, u128); 3] = [(0, 0, 0, 0); 3];
    
    for i in 0..ROUNDS.len() {
        let input_vector: Vec<usize> = build_input_vector(ROUNDS[i]);
        filter.reset();
        let timer = Instant::now();
        for i in 0..ROUNDS[i] {
            filter.add(&input_vector[i].to_string());
        }
        let total_insertion_time = timer.elapsed().as_nanos();

        for i in 0..1000 {
            filter.contains(&i.to_string());
        }
        let total_search_time = timer.elapsed().as_nanos() - total_insertion_time;

        statistics[i] = (
            ROUNDS[i],
            total_insertion_time,
            (total_insertion_time) / (ROUNDS[i] as u128),
            total_search_time
        );
    }
    statistics
}