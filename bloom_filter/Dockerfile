# Use a slim Python 3.11 base image
FROM python:3.11.2-slim

# Avoid interactive prompts during apt installs
ENV DEBIAN_FRONTEND=noninteractive

# Install system dependencies for Rust and Python
RUN apt-get update && apt-get install -y \
    curl \
    build-essential \
    pkg-config \
    libssl-dev \
    git \
    ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# Install Rust using rustup (non-interactive)
RUN curl https://sh.rustup.rs -sSf | sh -s -- -y
ENV PATH="/root/.cargo/bin:${PATH}"

# Set the working directory
WORKDIR /app

# Copy your entire project
COPY . .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Run the Rust project in release mode
CMD ["cargo", "run", "--release"]