package com.sanim.articular;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginTesting {
    @Test
    public void logingUserTesting(){
        FirebaseAuth auth = Mockito.mock(FirebaseAuth.class);
        final Task<AuthResult> mockedAuth = Mockito.mock(Task.class);
        when(auth.signInWithEmailAndPassword("testing2@gmail.com","testing2"))
                .thenReturn(mockedAuth);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Task<AuthResult> authResult = invocation.getArgument(0,Task.class);
                assertEquals(true,authResult.isSuccessful());
                return null;
            }
        });

    }
}