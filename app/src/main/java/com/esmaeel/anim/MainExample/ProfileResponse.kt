package com.esmaeel.anim.MainExample

data class ProfileResponse(
    val `data`: Data? = Data(),
    val msg: List<String?>? = listOf(),
    val status: Boolean? = false
) {
    data class Data(
        val user: User? = User()
    ) {
        data class User(
            val address: Any? = Any(),
            val city: City? = City(),
            val city_id: String? = "",
            val coming_service: ComingService? = ComingService(),
            val country: Country? = Country(),
            val country_id: String? = "",
            val credit: String? = "",
            val currency: String? = "",
            val email: String? = "",
            val email_notification: String? = "",
            val favorites_count: String? = "",
            val first_name: Any? = Any(),
            val followers_count: String? = "",
            val following_count: String? = "",
            val id: Int? = 0,
            val image: String? = "",
            val last_name: Any? = Any(),
            val locale: String? = "",
            val marketplace: String? = "",
            val mobile_notification: String? = "",
            val name: String? = "",
            val notifications_count: Int? = 0,
            val orders_count: String? = "",
            val phone: String? = "",
            val promo_code_value: String? = "",
            val qr_code: String? = "",
            val reservations_count: String? = "",
            val status: String? = "",
            val user_number: String? = ""
        ) {
            data class City(
                val city_name: String? = "",
                val country_id: String? = "",
                val id: Int? = 0
            )

            data class ComingService(
                val branch_id: String? = "",
                val city: String? = "",
                val discount_amount: String? = "",
                val employee: Employee? = Employee(),
                val employee_id: String? = "",
                val fawry_payment_status: Any? = Any(),
                val id: Int? = 0,
                val old_service_time: Any? = Any(),
                val payment_method: String? = "",
                val payment_type: String? = "",
                val price: String? = "",
                val reservation_status_word: String? = "",
                val reserved_by_salon: String? = "",
                val salon_home_status: String? = "",
                val salon_logo: String? = "",
                val salon_name: String? = "",
                val service: Service? = Service(),
                val service_date: String? = "",
                val service_id: String? = "",
                val service_time: String? = "",
                val status: String? = "",
                val user_id: String? = "",
                val user_status: String? = ""
            ) {
                data class Employee(
                    val branch_id: String? = "",
                    val employee_name: String? = "",
                    val id: Int? = 0,
                    val image: String? = "",
                    val salon_id: String? = ""
                )

                data class Service(
                    val app_views: String? = "",
                    val currency: String? = "",
                    val home_price: String? = "",
                    val id: Int? = 0,
                    val image: String? = "",
                    val rate: String? = "",
                    val salon_price: String? = "",
                    val service_category_id: String? = "",
                    val service_description: String? = "",
                    val service_duration: String? = "",
                    val service_name: String? = "",
                    val service_price: String? = "",
                    val watching_users: List<WatchingUser?>? = listOf(),
                    val watching_users_count: Int? = 0,
                    val web_views: String? = ""
                ) {
                    data class WatchingUser(
                        val id: Int? = 0,
                        val image: String? = "",
                        val marketplace: String? = "",
                        val notifications_count: Int? = 0
                    )
                }
            }

            data class Country(
                val country_name: String? = "",
                val currency_id: String? = "",
                val id: Int? = 0
            )
        }
    }
}