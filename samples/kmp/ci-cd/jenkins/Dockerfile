FROM jenkins/jenkins:lts-jdk21

USER root


RUN apt-get update && apt-get install -y \
    unzip wget curl \
    ruby ruby-dev build-essential \
    libffi-dev libssl-dev gnupg2 zlib1g-dev liblzma-dev \
 && apt-get clean

ENV ANDROID_SDK_ROOT=/opt/android-sdk
ENV PATH="$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools:$PATH"

RUN mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    cd       $ANDROID_SDK_ROOT/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip -O tools.zip && \
    unzip -q tools.zip && rm tools.zip && mv cmdline-tools latest

RUN yes | sdkmanager --licenses && \
    sdkmanager "platform-tools" "platforms;android-34" "build-tools;35.0.0"

RUN chown -R jenkins:jenkins $ANDROID_SDK_ROOT

RUN gem install bundler fastlane -NV

USER jenkins
