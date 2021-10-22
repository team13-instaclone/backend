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
  > To clone code Instagram for interpreting how Instagram or other SNS services operates
  
  ## 💡 API for this project
  
  > **Main Page(all posts) api**  
  **URL :** /api/main
  **request : (Type: Get)
  **response :** '{"result":"success","data":{[{"postid" : 게시물 고유 아이디,"username":작성자이름, "image": 이미지 경로,"date": 작성날짜,"content": 게시글},{"postid" : 게시물 고유 아이디,"username":작성자이름,"image": 이미지 경로,"date": 작성날짜, "content": 게시글}] }}

  > **Creating post api**  
  **URL :**/api/post  
  **request :**{"username": 아이디,"content": 게시글,"image": 사진}
  **response :** {"result":"success","data": "게시물 업로드 성공"}  
  
   > **Detail of the post api**  
  **URL :** /api/detail
  **request : ** {"postid":게시물id}
  **response :** {"result":"success","data":{"postid" : 게시물 아이디,"username":작성자이름, "image": 이미지 경로, "date": 작성날짜, "content": 게시글}}

 > **Delete post api**  
  **URL :** /api/post
  **request : ** {"postid":게시물id}
  **response :** {"result":"success","data":"게시물 삭제 성공"}
  
   > **Edit post api**  
  **URL :** /api/post
  **request : ** {"postid":게시물id,"username": 아이디,"content": 게시글,"image": 사진}
  **response :** {"result":"success","data":{"postid" : 게시물 아이디,"image": 이미지 경로, "username":작성자, "date": 작성날짜,"content": 게시글 }}
  
   > **Comment View api**  
  **URL :** /api/comment
  **request : ** {"postid":게시물id}
  **response :** {"result":"success","data":{"commentid":댓글아이디,"comment":댓글내용,"username":아이디, "date":댓글작성날짜}}
  
   > **Creating Comment api**  
  **URL :** /api/comment
  **request : ** {"postid": 게시물아이디, "username": 아이디, "comment": 댓글내용}
  **response :** {"result":"success","data":{"postid":게시물 아이디,"commentid": 코멘트 아이디, "username":아이디, "comment": 댓글내용, "date": 작성날짜}}
  
  > **Deleting Comment api**  
  **URL :** /api/comment
  **request : ** {"commentid":댓글아이디}
  **response :** {"result":"success","data":"댓글삭제 성공"}
  
   > **Editing Comment api**  
  **URL :** /api/comment
  **request : ** {"commentid":댓글아이디,"comment": 댓글내용}
  **response :** {"result":"success","data":{"commentid":댓글아이디,"comment":댓글내용, "username":작성자,"date":댓글작성날짜}}
  
  
 
 
  > **Login api**  
  **URL :** /api/user/login
  **request :** {'id':id, 'password':password}  
  **response :** {"result":"success","data":{"token":토큰,"username":아이디,"name":이름}}
  
  > **Creating Account api**  
  **URL :**  /api/user/signup
  **request :** {"username": 아이디, "name": 이름, "pwd": password} 
  **response :** {"result":"success","data":"회원가입성공"} 
  
   > **Log out api**  
  **URL :**  /api/user/logout
  **request :** 
  **response :** {"result":"success","data":"로그아웃성공"}
  
   > **Log out api**  
  **URL :**  api/user/redunancy
  **request :** {"username": 아이디}
  **response :** {"result":"success","data":"중복되지 않습니다"}
  
  

 

  
  
 
  
  
 

  ***
  # 화면 구성
  ***로그인 페이지***  
    
 
  ***회원 가입 페이지***  
  
  ***버튼클릭, 리뷰작성 모습(33fps라 화질이 깨짐)***  
 
  ## 관련 링크
    
  # Solved Problems
 
    
  
  
