FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    openjdk-21-jdk \
    protobuf-compiler \
    make \
    && apt-get clean

# Set working directory
WORKDIR /workspace

# Copy project
COPY . .

# Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the plugin
RUN ./gradlew :protoc-plugin:installDist

# Set environment variables
ENV PATH="/workspace/protoc-plugin/build/install/protoc-plugin/bin:$PATH"

# Default shell
CMD ["bash"]
