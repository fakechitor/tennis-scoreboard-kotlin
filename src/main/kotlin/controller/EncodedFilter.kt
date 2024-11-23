package controller

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter

@WebFilter(urlPatterns = ["/"])
class EncodedFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request.characterEncoding = "UTF-8"
        response.characterEncoding = "UTF-8"
        chain.doFilter(request, response)
    }
}