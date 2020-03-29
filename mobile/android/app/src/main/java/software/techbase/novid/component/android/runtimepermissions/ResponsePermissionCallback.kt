package software.techbase.novid.component.android.runtimepermissions

/**
 * Created by Wai Yan on 3/30/19.
 */
interface ResponsePermissionCallback {
    fun onResult(permissionResult: List<String>)
}