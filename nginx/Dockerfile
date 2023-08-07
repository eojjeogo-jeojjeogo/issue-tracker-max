FROM nginx:latest

# nginx.conf 를 아래 경로에 복사
COPY ./nginx.conf /etc/nginx/nginx.conf

# 80 포트 오픈
EXPOSE 80

# container 실행 시 자동으로 실행할 command. nginx 시작함
CMD ["nginx", "-g", "daemon off;"]
