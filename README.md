# withdrawal-proj
Development project for a withdrawal system


## DevContainer 실행 방법

1. **VSCode에서 Dev Containers 확장 설치**

    * Extensions에서 `Dev Containers` 검색 후 설치합니다.

2. **프로젝트 폴더 열기**

    * VSCode에서 `withdrawal-proj` 프로젝트 루트를 엽니다.

3. **DevContainer 실행**

    * Command Palette 열기

        * Windows/Linux: `Ctrl + Shift + P`
        * macOS: `Cmd + Shift + P`
    * 다음 명령어 검색 후 실행

      ```
      Dev Containers: Rebuild and Reopen in Container
      ```

4. **Docker 이미지 빌드 및 컨테이너 생성**

    * VSCode가 자동으로 Docker 이미지를 빌드하고
      DevContainer 환경으로 진입합니다.

5. **컨테이너 내부에서 Spring Boot 실행**
   DevContainer 터미널에서 아래 명령어 실행

   ```
   ./gradlew bootRun
   ```
<br>

Dev Container 실행시 DB, Redis도 함께 기동되며, DB 세팅도 함께 진행됩니다.
