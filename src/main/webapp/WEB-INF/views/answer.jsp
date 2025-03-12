<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결과 페이지</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<%= request.getContextPath() %>/assets/answer_style.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <% if (session.getAttribute("answer") != null) { %>
            <div class="result-card">
                <div class="result-header">
                    <h2 class="text-center">결과 페이지</h2>
                </div>

                <div class="result-item">
                    <span class="result-label">Question:</span>
                    <p class="lead"><%= session.getAttribute("question")%></p>
                </div>

                <div class="result-item">
                    <span class="result-label">Answer:</span>
                    <p class="lead"><%= session.getAttribute("answer")%></p>
                </div>

                <div class="result-item">
                    <details>
                        <summary>
                            <span class="result-label">Thinking</span>
                        </summary>
                        <p><%= session.getAttribute("thinking")%></p>
                    </details>
                </div>

                <div class="result-item">
                    <span class="result-label">Reasoning:</span>
                    <p><%= session.getAttribute("reasoning")%></p>
                </div>

                <% if (session.getAttribute("image") != null) { %>
                <div class="text-center">
                    <img class="result-image img-fluid"
                         alt="<%= session.getAttribute("answer")%>"
                         src="<%= session.getAttribute("image")%>">
                </div>
                <% } %>

                <div class="text-center copy-btn">
                    <button class="btn btn-primary" onclick="copyToClipboard()">주소 복사하기</button>
                    <span id="copySuccess" class="copy-success">복사되었습니다!</span>
                </div>
            </div>
            <% } else { %>
            <div class="result-card text-center">
                <h3>표시할 결과가 없습니다.</h3>
            </div>
            <% } %>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<!-- Copy URL Script -->
<script>
    function copyToClipboard() {
        // Create a temporary input element
        const tempInput = document.createElement("input");
        // Set its value to the current URL
        tempInput.value = window.location.href + "/<%= session.getAttribute("uuid") %>";
        // Append it to the body
        document.body.appendChild(tempInput);
        // Select all the text
        tempInput.select();
        // Copy the text
        document.execCommand("copy");
        // Remove the temporary element
        document.body.removeChild(tempInput);

        // Show success message
        const successMsg = document.getElementById("copySuccess");
        successMsg.style.display = "inline";

        // Hide success message after 3 seconds
        setTimeout(function() {
            successMsg.style.display = "none";
        }, 3000);
    }
</script>
</body>
</html>