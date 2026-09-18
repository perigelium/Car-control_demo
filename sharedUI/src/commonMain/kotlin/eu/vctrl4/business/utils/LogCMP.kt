package eu.vctrl4.business.utils

object Log {
    fun d(tag: String, msg: String) = println("[D/$tag]: $msg")
    fun e(tag: String, msg: String) = println("[E/$tag]: $msg")
    fun e(tag: String, msg: String, tr: Throwable) {
        println("[E/$tag]: $msg")
        tr.printStackTrace()
    }
    // i, w, v ...
}