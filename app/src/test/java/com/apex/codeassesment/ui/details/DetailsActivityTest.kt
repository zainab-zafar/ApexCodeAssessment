package com.apex.codeassesment.ui.details

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apex.codeassesment.data.model.Name
import com.apex.codeassesment.data.model.User
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class DetailsActivityTest {

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        mockContext = Mockito.mock(Context::class.java)
    }

    @Test
    fun `Details Activity `() {
        val intent = Bundle()
            .apply {
                this.putParcelable("saved-user-key", User(name = Name(first = "sample", last = "test")))
            }

        val scenario = launch(DetailsActivity::class.java, intent)

        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            activity.initData()
            TestCase.assertEquals("sample", activity.binding.detailsName.text)
        }
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }
}