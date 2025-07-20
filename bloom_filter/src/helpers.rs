use std::io;
use std::fs;
use std::io::Write;

pub fn prepend_to_file_text(file_path: &str, text: &str) -> io::Result<()> {
    let file_text = fs::read_to_string(file_path)?;
    let new_content: String = format!("{}{}{}", text, "\n", file_text);
    fs::write(file_path, new_content)?;
    Ok(())
}

pub fn create_base_plot_script() -> io::Result<()> {
    let base_file = fs::read_to_string("plot_script_base.py")?;
    let mut new_file = fs::File::create("plot_script.py")?;
    new_file.write_all(base_file.as_bytes())?;
    Ok(())
}