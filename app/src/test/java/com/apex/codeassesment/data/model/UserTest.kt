package com.apex.codeassesment.data.model

import android.content.Context
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class UserTest {

    private lateinit var mockContext: Context

    @Before
    fun setup() {
        mockContext = Mockito.mock(Context::class.java)
    }

    @Test
    fun `create random user`() {
        TestCase.assertEquals(25, User.createRandom().dob?.age)
    }
}