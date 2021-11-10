# Team
 석지선, 양희준, 안정우, 오성현, 심우진, 김갑민
  
# Stack
  * Spring
  * React
  * MySQL
  * S3
  * JWT
  * Html, css, Jquery
  
  # For what we made this project
  > To clone code Instagram for interpreting how Instagram or other SNS services operate

  # DB diagram
  
<img width="500" alt="Screen Shot 2021-10-22 at 8 10 13 PM" src="https://user-images.githubusercontent.com/90609214/138444462-7ca47df9-1cf9-4091-99d2-1b27092d6936.png">

  
  ## 💡 API for this project
 
  ![api1](https://user-images.githubusercontent.com/78577071/138445224-a02d10d1-4236-4786-b049-64d9ba9add99.png)

 ![api2](https://user-images.githubusercontent.com/78577071/138445236-e14aba7b-3c6e-4954-97a5-a84da880a3ef.png)
![api3](https://user-images.githubusercontent.com/78577071/138445238-a9323d0b-318a-467f-90d1-cfdb05629200.png)

  ***
    
  # Solved Problems
  - 이미지파일 S3에 업로드하기
  순서대로 적어보면, 

1. gradle에 관련 라이브러리 추가
    
    ```java
    //s3와 연결위해
        implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.228')
        implementation 'com.amazonaws:aws-java-sdk-s3'
    ```
    
2. S3 버킷을 만든다.
    이때, 퍼블릭 엣세스차단을 풀었음. 차단해도 되는지는 아직 안해봐서 모르겠는데, 현재는 차단을 풀어서 하니 된 상태이다.
3. access-id, access-pw를 만든다. 이때 엑셀파일로 다운받아짐.   
4. IAM 설정 EC2에서 해주기.
   EC2에서  아무 S3에나 데이터를 쏠 수는 없게 막아져있다. IAM추가해주면 된다. 추가할 권한은 AmazonS3FullAccess이다.
5. S3Uploader클래스를 만든다
이 클래스에 S3에 내가 만든 버킷에 사진을 업로드하는 기능을 넣으면 된다.

이 클래스가 갖는 주요기능:

- 파일을 로컬(또는 EC2)에 업로드하기
- 로컬(또는 EC2)에 업로드한 파일을 S3로 업로드하기
- S3에 업로드했으면 로컬(또는 EC2)에 저장된 이미지 지우기

6. 이제 서비스에서 위 S3Uploader를 사용하면 된다. 
    
  
  
