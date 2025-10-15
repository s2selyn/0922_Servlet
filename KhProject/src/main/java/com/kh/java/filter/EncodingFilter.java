package com.kh.java.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * 바깥에서 오는 요청이 서블릿에 들어가기 전에 그 사이에 들어감
 * 어떤 서블릿에 갈 때 거칠 지 정할 수 있는데 /*로 작성하면
 * 모든 서블릿에 도달하기전에 이 필터를 거치겠다.
 * 는 뜻이 됨
 */
@WebFilter("/*")
public class EncodingFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public EncodingFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here <- 어따 친절허다, 여기에서 작업하면 됨, 서블릿에 넘어가기 전에 공통으로 수행하고 싶은 코드 작성
		request.setCharacterEncoding("UTF-8");
		// POST방식 갈때마다 썼던 중복코드, 모든 서블릿에 가기 전에 이 필터로 거쳐서 인코딩 바뀜

		// pass the request along the filter chain
		chain.doFilter(request, response);
		// 이건 다른 필터가 있다면 그걸 거쳐서 가도록 하고, 없다면 서블릿으로 가게 하는 코드
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
