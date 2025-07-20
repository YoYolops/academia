# How to run the code:
**YOU DON'T NEED TO !!!**
All the graphics are already generated, but if you want to run the code in your machine:
**USING DOCKER**:
```bash
docker build -t bloom-runner . && docker run --rm -v "$(pwd)/plots:/app/plots" bloom-runner
```

**WITHOUT DOCKER**
```bash
cargo run --release
```
in the root folter.
Needless to say you'll need cargo installed:
https://doc.rust-lang.org/cargo/getting-started/installation.html

The flag `--release` is essential, since we are using integer overflow as feature and rust panics if it happens in dev mode.

**Important**: If you run the rust program, all plots inside folder `/plots` will be overwritten, and since the plots in this markdown all point there, they will change here too!

**Also Important**: you will need the packages inside `requiremenst.txt` to be installed globally if running without Docker

# What was made by LLM?
All the python plot functions
the djb2 and sdbm hashing functions
and with a couple of ideas that i pointed as commentaries in the code.

### Why rewrite everything in rust?
Because i wanted to! I really found bloom filters interesting and i wanted to implement it using bitwise operations. Also, i'm learning rust and i tought it could be a good learning experience.

Rewriting the entire code and adapting the `add()` and `contains()` functions to make use of bitwise operations really made me go over the algorithm's ins and outs. 

Furthermore, almost all of the pieces of information i found about bloom filters gave the idea of bloom filters operating over bits. Having an array of booleans is a very nice abstraction of that, but it is too easy. Bitwise operations are an old concept to me, but a very new practice, so i might as well make it more familiar, right? We are in college after all, time to learn, folks!

But this comes with a few caveats, bringing in our next topic

### What are the differences between the original python version and mine?
Firts of all, the rust version uses a 128bit number as its bit_array instead of an array of booleans. This brings a little problem that i only noticed later. Bloom Filters, at least the way we implemented, are not extensible, meaning once we declare it with a given size, we cannot alter without rendering it unusable. That's ok and happens both in the python and in the rust version.

But the max size of rust's version is 128 (the max integer value possible), while the python's doesn't have an initial limit because it uses an array. I could make a rust version that used an array of booleans instead, like python's, but i'm already struggling with time so we'll keep on with what we have.

WHY IS THAT A PROBLEM?
Simply because the exercise 2 states a few parameters to be set as default. One of them is `m`, which is, in the python's version, the bit array size:

```markdown
1. **Configuração do experimento**:
   - m = 512K posições // THIS EXCEEDS A LOT OUR 128 LENGTH LIMIT
   - dois niveis de k
      - $k_1$ = 128 funções hash
      - $k_2$ = 768 funções hash
   - Teste com 100, 1000 e 5000 elementos
```
### What our code will do:

First, inside of `main.rs` we create our `plot_script.py` by copying the content of `plot_script_base.py` into the new file, overwriting if already created. This is important for avoiding data conflict when rust runs multiple times:
```rust
helpers::create_base_plot_script()?;
```
```rust
pub fn create_base_plot_script() -> io::Result<()> {
    let base_file = fs::read_to_string("plot_script_base.py")?;
    let mut new_file = fs::File::create("plot_script.py")?;
    new_file.write_all(base_file.as_bytes())?;
    Ok(())
}
```

This python script will generate all of our plots and store it in `/plots/*.png` and it was entirely ChatGPT made.
Afterwards, we create all the three filters needed to run our first experiments. Each will be inputed to our `test_filter_consistency` function under `testers.rs` module:

```rust
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
```
Generating a specific statistical tuple array for each filter inputed. as previously shown, the tuple's values meaning are `(num_inserted_items; num_collision_til_now; num_false_positives, filter_occupation)`
After saving all results:

```rust
let consistency_result_md5_sha2 = testers::test_filter_consistency(&mut filter_md5_sha2);
let consistency_result_djb2_sdbm = testers::test_filter_consistency(&mut filter_djb2_sdbm);
let consistency_result_xxhash = testers::test_filter_consistency(&mut filter_xxhash);
```

whe turn each of the arrays in strings with `format!("{:?}", filter)` and append it to a base string equivalent to a python's variable declaration:

```rust
let python_text: String = 
    String::from("consistency_result_md5_sha2 = ") + &format!("{:?}", consistency_result_md5_sha2) + "\n" + 
    &String::from("consistency_result_djb2_sdbm = ") + &format!("{:?}", consistency_result_djb2_sdbm) + "\n" + 
    &String::from("consistency_result_xxhash = ") + &format!("{:?}", consistency_result_xxhash);
```
Then we just need to inject this data into our python script to properly generate our plots:

```rust
helpers::prepend_to_file_text("plot_script.py", &python_text)?;
```
```rust
pub fn prepend_to_file_text(file_path: &str, text: &str) -> io::Result<()> {
    let file_text = fs::read_to_string(file_path)?;
    let new_content: String = format!("{}{}{}", text, "\n", file_text);
    fs::write(file_path, new_content)?;
    Ok(())
}
```

For our second experiment, we do the exact same thing. The only thing that changes is the meaning of the tuples generated by the `test_filter_time` funtion. Check it here:

```rust
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
```

Finally, we prepend the time test results into the python script and run it:

```rust
let python_plot_script_execution = std::process::Command::new("python3")
    .arg("./plot_script.py")
    .status()
    .expect("Panicked while trying to execute python plot script");

if python_plot_script_execution.success() {
    println!("The experiments plots were generated and stored in /plots/*.png")
} else {
    println!("Failed to generate plots due to python execution error :(")
}
```

# Results Experiment 1
### Collisions over time
![Collisions over time](./plots/collisions.png)

We can see that, the simpler the hash strategy, the most collisions over time. This is a consequence of the avalanche effect discussed in class. Strategies such as SHA2 have a greater avalanche effect than simpler strategies such as xxhash, impacting in the collision graphic.

### Occupation over time
![Occupation over time](./plots/occupation.png)

Here we see something i wasn't expecting. The first one to achieve 100% occupation is the more complex strategy (sha2+md5), while simpler ones take longer. Why?

### False positives over time
![False positives over time](./plots/false_positives.png)

Just realized i have forgotten to mark the 1% false positives point on the graphic. By looking (and guessing a little), the first one to achieve 1% false positive is xxhash. It has a very fast growth for the first 20% of false positives, which doesn't come as a surprise since xxhash is focused on speed rather than collision resistance.

Also we see, in conformity with the occupation over time graphic, that the filter hits 100% false positives exactly when occupation is full (all bits set to one), which is expected from the nature of bloom filter's search.

### Experiment 1 Question's answers:
   1. The best strategy for lessening the false positives amount seems to be, in this scenario, md5+sha2. At least for the first 10 items inserted. Strategies seem to have different behaviors on different occupancy stages.
   2. Yes, the occupation level is the same when the first false positives start appearing

# Results Experiment 2
### Insertion time with different Ks on the same strategy:
![Average insertion time for sha+md5](./plots/avg_insert_sha.png)
![Average insertion time for sdbm+djb2](./plots/avg_insert_djb2.png)
![Average insertion time for xxhash](./plots/avg_insert_xxhash.png)

We can see the huge impact of a greater K in performance for every strategy.
A surprise was xxhash strategy with **larger K** being faster for thousands of items on average than for hundreds, while other strategies tend to lose performance for greater amount of insertions.

When the strategies with **lower K value** we see that all of them seem to perform better for large amount of insertions and, surprisingly, keeping steady timewise with larger insertions

### Insertion Times By Ks:
![Insertion time K1](./plots/avg_insert_time_k1.png)
![Insertion time K2](./plots/avg_insert_time_k2.png)

### Comparing all of them (log scale for better visuals):
![Insertion Time all](./plots/grouped_insertion_time.png)

### Thousand Search Time By Strategy
![Search time for sha+md5](./plots/search_time_sha.png)
![Search time for sdbm+djb2](./plots/search_time_djb2.png)
![Search time for xxhash](./plots/search_time_xxhash.png)

Here we see that hash strategies also impact on search. Everything as expected, the most complex hash strategy produces the greatests times.

# Final Considerations:
It is indeed a linear complex scaling.

We saw greater K values heavily impacting on algorith performance. Also, more complex strategies, such as cryptographic hashes, also heavily impact performance.
Since on the original amazon problem we would have an tremendous filter size, i think performance would be better than accuracy, so i would go with something like xxhash or djb2+sdbm.

A slower strategy would be better in systems were accuracy is critical, SHA2+MD5 are very slow, but provide a good collision resistance.

# My questions:
Why running the input value multiple times through the hashing function (K_ROUNDS)?
Since the consistency tests were all made with the same `K_ROUNDS` value, it didn't show the difference in accuract between different K_ROUNDS.
