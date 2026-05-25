# Image Maven để build file JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy file cấu hình pom.xml và toàn bộ code vào container
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src

# Lệnh build api, bỏ qua chạy thử nghiệm (test) để build nhanh hơn
RUN ./mvnw clean package -DskipTests

# Sử dụng Image JRE để chạy api
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy file .jar đã build xong từ bước 1 sang bước 2
COPY --from=build /app/target/*.jar app.jar

# Khai báo biến môi trường múi giờ để tránh lỗi Asia/Saigon
ENV TZ=Asia/Ho_Chi_Minh

# Mở cổng 8080 trong container
EXPOSE 8080

# Lệnh khởi chạy Spring Boot bên trong container
ENTRYPOINT ["java", "-Duser.timezone=Asia/Ho_Chi_Minh", "-jar", "app.jar"]