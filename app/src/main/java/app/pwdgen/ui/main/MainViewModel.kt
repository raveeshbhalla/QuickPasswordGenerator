package app.pwdgen.ui.main

import androidx.lifecycle.ViewModel
import app.pwdgen.PasswordGenerator
import io.reactivex.subjects.BehaviorSubject
import kotlin.properties.Delegates

class MainViewModel : ViewModel() {
    var lower: Boolean by Delegates.observable(true) {
        _,_,_ -> generateNewPassword()
    }
    var upper: Boolean  by Delegates.observable(true) {
        _,_,_ -> generateNewPassword()
    }
    var digits: Boolean  by Delegates.observable(true) {
        _,_,_ -> generateNewPassword()
    }
    var special: Boolean  by Delegates.observable(true) {
        _,_,_ -> generateNewPassword()
    }

    var length: Int by Delegates.observable(8) {
        _,_,_ -> generateNewPassword()
    }

    var pwd: String = PasswordGenerator(lower, upper, digits, special).generate(length)
        set(value) {
            field = value
            pwdObservable.onNext(field)
        }

    val pwdObservable: BehaviorSubject<String> = BehaviorSubject.createDefault(pwd)

    fun generateNewPassword() {
        pwd = PasswordGenerator(lower, upper, digits, special).generate(length)
    }
}