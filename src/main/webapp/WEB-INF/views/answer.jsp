<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><%= request.getAttribute("question") != null ? request.getAttribute("question") : "결과 페이지" %></title>

    <!-- Standard Meta Tags -->
    <meta name="description" content="<%= request.getAttribute("answer") != null ? request.getAttribute("answer") : "결과 페이지입니다." %>">
    <meta name="keywords" content="결과, 질문, 답변, JSP">

    <!-- Open Graph Meta Tags -->
    <meta property="og:title" content="<%= request.getAttribute("question") != null ? request.getAttribute("question") : "결과 페이지" %>">
    <meta property="og:description" content="<%= request.getAttribute("answer") != null ? request.getAttribute("answer") : "결과 페이지입니다." %>">
    <meta property="og:type" content="website">
    <meta property="og:url" content="<%= request.getRequestURL() %>">
    <% if (request.getAttribute("image") != null) { %>
    <meta property="og:image" content="<%= request.getAttribute("image") %>">
    <% } %>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

    <link href="<%= request.getContextPath() %>/assets/answer_style.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <%-- request에 "answer" 속성이 있는 경우 결과 카드 출력 --%>
            <% if (request.getAttribute("answer") != null) { %>
            <div class="result-card">
                <%-- 결과 페이지 제목 --%>
                <div class="result-header">
                    <h2 class="text-center">결과 페이지</h2>
                </div>

                <%-- 질문 출력 --%>
                <div class="result-item">
                    <span class="result-label">Question:</span>
                    <p class="lead"><%= request.getAttribute("question") %></p>
                </div>

                <%-- 답변 출력 --%>
                <div class="result-item">
                    <span class="result-label">Answer:</span>
                    <p class="lead"><%= request.getAttribute("answer") %></p>
                </div>

                <%-- Thinking 상세 내용 (접기/펼치기 가능) --%>
                <div class="result-item">
                    <details>
                        <summary>
                            <span class="result-label">Thinking</span>
                        </summary>
                        <p><%= request.getAttribute("thinking") %></p>
                    </details>
                </div>

                <%-- Reasoning 출력 --%>
                <div class="result-item">
                    <span class="result-label">Reasoning:</span>
                    <p><%= request.getAttribute("reasoning") %></p>
                </div>

                <%-- 이미지가 존재하는 경우 이미지 출력 --%>
                <% if (request.getAttribute("image") != null) { %>
                <div class="text-center">
                    <img class="result-image img-fluid"
                         loading="lazy"
                         alt="<%= request.getAttribute("answer") %>"
                         src="<%= request.getAttribute("image") %>">
                </div>
                <% } %>

                <%-- 버튼 영역: URL 복사하기와 다시 하기 버튼 --%>
                <div class="copy-btn">
                    <%-- URL 복사하기 버튼 --%>
                    <button class="btn btn-primary" onclick="copyToClipboard()">주소 복사하기</button>
                    <%-- 다시 하기 버튼: 클릭 시 루트('/')로 이동 --%>
                    <button class="btn btn-secondary" onclick="goToHome()">다시 하기</button>
                </div>
                <div class="copy-btn">
                    <span id="copySuccess" class="copy-success">복사되었습니다!</span>
                </div>
            </div>
            <% } else { %>
            <%-- request에 결과가 없는 경우 --%>
            <div class="result-card text-center">
                <h3>표시할 결과가 없습니다.</h3>
                <button class="btn btn-primary mt-3" onclick="goToHome()">홈으로 돌아가기</button>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script>
    // 주소 복사하기 기능: 현재 URL을 클립보드에 복사합니다.
    function copyToClipboard() {
        // 최신 클립보드 API 사용 (가능한 경우)
        if (navigator.clipboard && window.isSecureContext) {
            navigator.clipboard.writeText(window.location.href).then(() => {
                showCopySuccess();
            }).catch(() => {
                // 실패할 경우 기존 방식으로 폴백
                legacyCopyToClipboard();
            });
        } else {
            // 비보안 컨텍스트 또는 미지원 브라우저에서는 기존 방식 사용
            legacyCopyToClipboard();
        }
    }

    // 기존 클립보드 복사 방식 (폴백용)
    function legacyCopyToClipboard() {
        // 임시 input 요소 생성
        const tempInput = document.createElement("input");
        tempInput.value = window.location.href;
        tempInput.style.position = "absolute";
        tempInput.style.left = "-9999px";
        // 임시 input 요소를 body에 추가
        document.body.appendChild(tempInput);
        // input의 내용을 선택
        tempInput.select();
        tempInput.setSelectionRange(0, 99999); // 모바일 지원
        // 선택된 내용을 클립보드에 복사
        document.execCommand("copy");
        // 임시 input 요소 제거
        document.body.removeChild(tempInput);

        showCopySuccess();
    }

    // 복사 성공 메시지 표시 함수
    function showCopySuccess() {
        const successMsg = document.getElementById("copySuccess");
        successMsg.style.display = "inline-block";

        // 3초 후에 복사 성공 메시지 숨김
        setTimeout(function() {
            successMsg.style.display = "none";
        }, 3000);
    }

    // '다시 하기' 버튼 클릭 시 루트('/')로 이동하는 함수
    function goToHome() {
        window.location.href = "<%= request.getContextPath() %>/";
    }

    // details 요소의 열림/닫힘 상태 전환 시 애니메이션 효과
    document.addEventListener('DOMContentLoaded', function() {
        const details = document.querySelectorAll('details');

        details.forEach(detail => {
            detail.addEventListener('toggle', function() {
                if (this.open) {
                    this.style.backgroundColor = '#e9ecef';
                } else {
                    this.style.backgroundColor = '';
                }
            });
        });
    });
</script>
</body>
</html>