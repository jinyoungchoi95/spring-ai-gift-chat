# spring-ai-gift-chat

## 채팅 API
```
POST /api/chat
```
- 자연어로 선물 추천을 요청할 수 있어야 한다
- 선물 추천 도우미의 역할을 가진다
- llm 호출 중 요류가 발생하는 경우 안내 메시지를 응답할 수 있어야 한다
- 요청의 주요 정보를 로그로 남긴다

### spec
**Request Body**
- `message` (String, 필수): 사용자 메시지
- `sessionId` (String, 선택): 대화 단위 식별자

**Response Body**
- `message` (String): AI 응답 메시지
- `sessionId` (String): 대화 단위 식별자
