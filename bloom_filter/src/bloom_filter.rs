const FILTER_MAX_SIZE: usize = 128;

pub struct BloomFilter {
    hash_k_rounds: usize,
    bit_array: u128,
    bit_array_size: usize,
    strategy: fn(&str, usize, usize) -> Vec<usize>,
    insertion_history: Vec<u128>
}

impl BloomFilter {
    pub fn new(
        hash_k_rounds: usize,
        bit_array_size: usize,
        strategy: fn(&str, usize, usize)->Vec<usize>
    ) -> Self 
    {
        if bit_array_size > FILTER_MAX_SIZE {
            panic!("The filter max size is {}", FILTER_MAX_SIZE)
        }
        BloomFilter {
            hash_k_rounds: hash_k_rounds,
            bit_array: 0b0, // Binary 0 with 128 bits
            bit_array_size: bit_array_size,
            strategy: strategy,
            insertion_history: Vec::new()
        }
    }

    pub fn add(&mut self, item: &str) {
        let mut all_bits_flipped: u128 = 0b0; // Alteration to 
        for i in (self.strategy)(item, self.bit_array_size, self.hash_k_rounds) {
            all_bits_flipped |= 0b1 << i;
        }
        self.bit_array |= all_bits_flipped; // shift left + bitwise or.
        self.insertion_history.push(all_bits_flipped);
    }

    pub fn contains(&self, item: &str) -> bool {
        for bit_position in (self.strategy)(item, self.bit_array_size, self.hash_k_rounds) {
            let checker: u128 = 0b1 << bit_position;
            if(self.bit_array & checker) == 0 {
                return false;
            }
        }
        true
    }

    // pub fn print_significant_bits(&self) {
    //     println!("{:b}", self.bit_array);
    // }

    pub fn occupancy(&self) -> usize {
        self.bit_array.count_ones() as usize
    }

    pub fn collisions_amount(&self) -> usize {
        let mut counter: usize = 0;
        if self.insertion_history.len() < 2 {
            return 0;
        }                                           
        // Very slow way of counting duplicates, but will be good enough
        for i in 0..(self.insertion_history.len()-1) {
            for j in (i+1)..(self.insertion_history.len()) {
                if self.insertion_history[i] == self.insertion_history[j] {
                    counter += 1;
                    break; // Without the break, we count the same collision multiple times
                }
            }
        }
        counter
    }

    pub fn insertions_amount(&self) -> usize {
        self.insertion_history.len()
    }

    pub fn reset(&mut self) {
        self.bit_array = 0b0;
        self.insertion_history = Vec::new();
    }

}