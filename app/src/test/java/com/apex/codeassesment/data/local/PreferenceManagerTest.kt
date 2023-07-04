package com.apex.codeassesment.data.local

import android.content.Context
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.main.MainActivity
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class PreferenceManagerTest {

    @Mock
    private lateinit var mockContext: Context
    lateinit var preferencesManager: PreferencesManager

    @Before
    fun setup() {
        mockContext = Mockito.mock(Context::class.java)
        preferencesManager = PreferencesManager()
        MainActivity.sharedContext = mockContext

    }

    @Test
    fun `Store user data in pref`() {
        val user = User(name = Name("John Doe", "John", "Doe"), gender = "male")
        preferencesManager.saveUser(user.toString())
        `Load user data in pref`()

    }

    private fun `Load user data in pref`() {
        TestCase.assertNull(preferencesManager.loadUser())

    }


}