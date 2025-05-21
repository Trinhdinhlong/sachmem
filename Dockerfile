# Sử dụng Maven để build ứng dụng
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Tạo thư mục làm việc
WORKDIR /app

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build dự án Maven và tạo file WAR
RUN mvn clean package -DskipTests

# Giai đoạn chạy: sử dụng Tomcat để deploy file WAR
FROM tomcat:10.1-jdk21

# Xóa ứng dụng mặc định trong Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã build vào thư mục webapps của Tomcat
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Mở cổng 8080
EXPOSE 8080

# Lệnh khởi chạy Tomcat
CMD ["catalina.sh", "run"]
