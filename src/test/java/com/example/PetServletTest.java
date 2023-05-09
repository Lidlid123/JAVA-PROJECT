package com.example;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class PetServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private PetServlet petServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        petServlet = new PetServlet();
    }

    @Test
    public void testDoPost() throws Exception {
        // Set up mock request parameters
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("petname")).thenReturn("testpet");

        // Call the doPost method
        petServlet.doPost(request, response);

        // Verify that the response was redirected to the index page
        verify(response).sendRedirect(anyString());
    }
}