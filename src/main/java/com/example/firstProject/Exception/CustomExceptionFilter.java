package com.example.firstProject.Exception;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class CustomExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (RuntimeException ex) {
      handleRuntimeException(response, ex);
    } catch (Exception ex) {
      handleGenericException(response, ex);
    }
  }

  private void handleRuntimeException(HttpServletResponse response, RuntimeException ex) throws IOException {
    System.err.println("A RuntimeException was caught in the filter: " + ex.getMessage());

    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.getWriter().write("Custom error message: " + ex.getMessage());
  }

  private void handleGenericException(HttpServletResponse response, Exception ex) throws IOException {
    System.err.println("An Exception was caught in the filter: " + ex.getMessage());

    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // 500 Internal Server Error
    response.getWriter().write("Internal server error: " + ex.getMessage());
  }
}
