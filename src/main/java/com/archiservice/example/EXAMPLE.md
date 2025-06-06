# ExampleController API 테스트 가이드

## 1. 성공 케이스 테스트

```bash
curl -X GET "http://localhost:8080/example/success" \
  -H "Content-Type: application/json" \
  | jq .
```

**예상 응답:**
```json
{
  "resultCode": 200,
  "codeName": "SUCCESS",
  "message": "성공했습니다.",
  "data": {
    "id": 1,
    "name": "홍길동"
  }
}
```

## 2. 비즈니스 예외 케이스 테스트 (BusinessException)

```bash
curl -X GET "http://localhost:8080/example/custom-error" \
  -H "Content-Type: application/json" \
  | jq .
```

**예상 응답 (전역 예외처리기에서 처리):**
```json
{
  "resultCode": 404,
  "codeName": "U404",
  "message": "사용자를 찾을 수 없습니다.",
  "data": null
}
```

## 3. 시스템 예외 케이스 테스트 (NullPointerException)

```bash
curl -X GET "http://localhost:8080/example/unhandled-error" \
  -H "Content-Type: application/json" \
  | jq .
```

**예상 응답 (전역 예외처리기에서 처리):**
```json
{
  "resultCode": 500,
  "codeName": "C500",
  "message": "서버 내부 오류가 발생했습니다",
  "data": null
}
```

## 추가 옵션

### jq 없이 테스트
```bash
curl -X GET "http://localhost:8080/example/success"
```

### 응답을 파일로 저장
```bash
curl -X GET "http://localhost:8080/example/success" \
  -H "Content-Type: application/json" \
  -o success_response.json
```

### Verbose 모드로 HTTP 헤더까지 확인
```bash
curl -X GET "http://localhost:8080/example/success" \
  -H "Content-Type: application/json" \
  -v
```

## 참고사항

- 모든 응답이 **HTTP 200 OK**로 반환됩니다
- 에러 여부는 `resultCode` 필드로 판단하세요
- `jq`가 설치되어 있으면 JSON이 예쁘게 포맷됩니다
- 포트가 8080이 아니라면 적절히 변경하세요
- 문제 혹은 개선 사항 있으면 알려주세요