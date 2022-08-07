package com.randev.belajar_mvi.data

/**
 * @author Raihan Arman
 * @date 07/08/22
 */
object Mapper {
    fun mapUserResponse(userResponse: UserResponse?): List<User>{
        val mapper: (Data?) -> User = {
            User(
                id = it?.id ?: 0,
                email = it?.email.orEmpty(),
                name = "${it?.firstName.orEmpty()} ${it?.lastName.orEmpty()}"
            )
        }

        return userResponse?.data?.map(mapper).orEmpty()
    }

    fun mapUserDetailResponse(userDetailResponse: UserDetailResponse?): User {
        return User(
            id = userDetailResponse?.data?.id ?: 0,
            email = userDetailResponse?.data?.email.orEmpty(),
            name = "${userDetailResponse?.data?.firstName.orEmpty()} ${userDetailResponse?.data?.lastName.orEmpty()}"
        )
    }
}