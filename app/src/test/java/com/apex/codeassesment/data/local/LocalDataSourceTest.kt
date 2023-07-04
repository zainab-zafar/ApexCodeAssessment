package com.apex.codeassesment.data.local

import android.content.Context
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apex.codeassesment.data.model.ResponseModel
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.ui.main.MainActivity
import com.squareup.moshi.Moshi
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class LocalDataSourceTest {

    @Mock
    private lateinit var mockContext: Context

    lateinit var localDataSourceTest: LocalDataSource
    lateinit var moshi: Moshi
    lateinit var preferencesManager: PreferencesManager

    @Before
    fun setup() {
        mockContext = Mockito.mock(Context::class.java)
        MainActivity.sharedContext = mockContext
        preferencesManager = PreferencesManager()
        moshi = Moshi.Builder().build()
        localDataSourceTest = LocalDataSource(preferencesManager, moshi)
    }

    @Test
    fun `save user locally`() {
        val list = ArrayList<User>()
        list.add(User.createRandom())
        val responseModel = ResponseModel(list)
        localDataSourceTest.saveUser(responseModel)
    }

    fun `load user locally`() {
        TestCase.assertNull(localDataSourceTest.loadUser())
    }
}