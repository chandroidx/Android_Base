package ai.deepfine.utility.error

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 2/1/21
 * @version 1.0.0
 */

class ServerError(message: String? = null) : Exception(message)
class NetworkDisconnect : Exception()

class FileSaveError(message: String? = null) : Exception(message)