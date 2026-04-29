# spring-ai-gift-chat

## 채팅 API
```
POST /api/gifts/recommend
```
- 자연어로 선물 추천을 요청할 수 있어야 한다
- 선물 추천 도우미의 역할을 가진다
- llm 호출 중 요류가 발생하는 경우 안내 메시지를 응답할 수 있어야 한다
- 빈 요청 메시지인 경우 에러를 반환한다
- ai chat 외부요청이 실패한 경우 에러를 반환한다
- 요청의 주요 정보를 로그로 남긴다
  - 요청별 고유 requestId를 MDC에 세팅한다
  - 사용자 메시지, 컨텍스트, 응답, 소요 시간을 로깅한다

### spec
**Request Body**
- `message` (String, 필수): 사용자 메시지
- `sessionId` (String, 선택): 대화 단위 식별자

**Response Body**
- `message` (String): AI 응답 메시지
- `sessionId` (String): 대화 단위 식별자

## LLM 추상화
- `ChatClient`를 추상화 한다
  - `GiftRecommendChatClient`로 추상화하고 요청 메시지를 받아 공통된 응답을 해주는 객체로 구현한다
  - Spring ai 구현체 외에 Claude Agent Sdk를 사용한 구현체를 추가한다
- 각 구현체는 빈 등록을 선택적으로 수행할 수 있도록 한다
