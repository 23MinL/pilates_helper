package org.example.pilates_helper.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.pilates_helper.model.dto.LLMResult;
import org.example.pilates_helper.service.SupabaseService;

import java.io.IOException;
import java.util.UUID;

@WebServlet ("/answer/*")
public class AnswerFromDBController extends Controller {
    final static SupabaseService supabaseService = SupabaseService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String uuid = req.getPathInfo().split("/")[1];
        LLMResult result = supabaseService.findByUUID(uuid);
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        req.setAttribute("question", result.question());
        req.setAttribute("answer", result.answer());
        req.setAttribute("thinking", result.thinking());
        req.setAttribute("reasoning", result.reasoning());
        req.setAttribute("image", result.image());
        req.setAttribute("uuid", uuid);
        view(req, resp, "answer");
    }
}
