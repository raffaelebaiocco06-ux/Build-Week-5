//package Build_week.build_week.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//public class TokenFilter extends OncePerRequestFilter {
//    private final TokenTools tokenTools;
//
//    public TokenFilter(TokenTools tokenTools) {
//        this.tokenTools = tokenTools;
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer "))throw new UnauthorizedException("Inserire il token nell'authorization header nel formato corretto");
//
//        String accesToken = authHeader.replace("Bearer ", "");
//
//        tokenTools.verifyToken(accessToken);
//
//        UUID userId = this.tokenTools.extractIdFromToken(accessToken);
//
//
//        Utente authenticatedUser = this.usersService.findById(userId);
//
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//
//
//        return new AntPathMatcher().match("/auth/**", request.getServletPath());
//
//    }
//}
