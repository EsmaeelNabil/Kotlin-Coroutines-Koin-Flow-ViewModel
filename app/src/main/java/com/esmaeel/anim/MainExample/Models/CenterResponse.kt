package com.esmaeel.anim.MainExample.Models

data class CenterResponse(
    val `data`: Data? = Data(),
    val msg: List<String?>? = listOf(),
    val pagination: Pagination? = Pagination(),
    val status: Boolean? = false
) {
    data class Data(
        val popular_center: List<PopularCenter?>? = listOf(),
        val random_order_key: Int? = 0,
        val salons: List<Salon?>? = listOf()
    ) {
        data class PopularCenter(
            val id: Int? = 0,
            val rating: String? = "",
            val salon_background: String? = "",
            val salon_logo: String? = "",
            val salon_name: String? = "",
            val search_category_name: String? = "",
            val verify_image: String? = ""
        )

        data class Salon(
            val area: Area? = Area(),
            val area_id: String? = "",
            val category: Category? = Category(),
            val category_id: String? = "",
            val city: City? = City(),
            val city_id: String? = "",
            val cover_image: String? = "",
            val currency_id: String? = "",
            val device_token: Any? = Any(),
            val email: String? = "",
            val fawry_payment_process: String? = "",
            val first_login_status: String? = "",
            val id: Int? = 0,
            val is_approved: String? = "",
            val is_open_now: Int? = 0,
            val is_popular: String? = "",
            val is_publish: String? = "",
            val locale: String? = "",
            val package_expiration_date: String? = "",
            val package_id: String? = "",
            val phone: String? = "",
            val profile_verified: String? = "",
            val qr_code: String? = "",
            val rate: String? = "",
            val rating: String? = "",
            val reservation_policy: Any? = Any(),
            val sales_code: String? = "",
            val salon_background: String? = "",
            val salon_description: Any? = Any(),
            val salon_logo: String? = "",
            val salon_name: String? = "",
            val salon_number: String? = "",
            val search_category_name: String? = "",
            val slug: String? = "",
            val status: String? = "",
            val verify_image: String? = "",
            val verify_type: String? = "",
            val views: String? = "",
            val whatsapp: Any? = Any()
        ) {
            data class Area(
                val area_name: String? = "",
                val city_id: String? = "",
                val id: Int? = 0
            )

            data class Category(
                val category_name: String? = "",
                val child_count: String? = "",
                val device_type: String? = "",
                val flag: String? = "",
                val id: Int? = 0,
                val image: String? = "",
                val parent_id: String? = ""
            )

            data class City(
                val city_name: String? = "",
                val country_id: String? = "",
                val id: Int? = 0
            )
        }
    }

    data class Pagination(
        val count: Int? = 0,
        val current_page: Int? = 0,
        val has_more_pages: Boolean? = false,
        val last_page: Int? = 0,
        val per_page: Int? = 0,
        val total: Int? = 0
    )
}