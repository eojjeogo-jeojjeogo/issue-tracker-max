# fe/Dockerfile
# Node.js를 기반으로 하는 이미지를 가져옵니다.
FROM node:16

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# package.json과 package-lock.json(만약 있다면)을 복사합니다.
COPY package*.json ./

# npm install로 프로젝트 의존성을 설치합니다.
RUN npm install

# React 앱 소스를 복사합니다.
COPY . .

# React 앱을 빌드합니다.
RUN npm run build