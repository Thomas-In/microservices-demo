FROM microsoft/dotnet:2.1-sdk-alpine as builder
WORKDIR /app
COPY . .
RUN dotnet restore && \
    dotnet build && \
    dotnet publish -c release -r linux-musl-x64 -o /cartservice

# cartservice
FROM alpine:3.8
# set CoreCLR tracing environment variables
ENV DOTNET_STARTUP_HOOKS=/app/Instana.Tracing.Core.dll
ENV CORECLR_ENABLE_PROFILING=1
ENV CORECLR_PROFILER={cf0d821e-299b-5307-a3d8-b283c03916dd}
ENV CORECLR_PROFILER_PATH=/cartservice/instana_tracing/CoreProfiler.so

# provide agent-endpoint
ENV INSTANA_AGENT_HOST=10.96.0.1
ENV INSTANA_AGENT_PORT=42699

RUN GRPC_HEALTH_PROBE_VERSION=v0.2.0 && \
    wget -qO/bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 && \
    chmod +x /bin/grpc_health_probe

# Dependencies for runtime
# busybox-extras => telnet
RUN apk add --no-cache \
    busybox-extras \
    libc6-compat \
    libunwind \
    libuuid \
    libgcc \
    libstdc++ \
    libintl \
    icu
WORKDIR /app
COPY --from=builder /cartservice .
ENTRYPOINT ["./cartservice", "start"]
