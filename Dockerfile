FROM ubuntu:22.04

RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    openjdk-21-jdk \
    protobuf-compiler \
    make \
    && apt-get clean

WORKDIR /workspace

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew :protoc-plugin:installDist

# ➡플러그인 실행 스크립트 추가
RUN echo '#!/bin/bash\nexec java -cp "/workspace/protoc-plugin/build/install/protoc-plugin/lib/*" io.github.protogenerator.plugin.StrictProtoGenerator' > /workspace/scripts/protoc-gen-strictproto && \
    chmod +x /workspace/scripts/protoc-gen-strictproto

ENV PATH="/workspace/protoc-plugin/build/install/protoc-plugin/bin:$PATH"

CMD ["bash"]
