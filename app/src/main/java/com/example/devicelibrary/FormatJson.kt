package com.example.devicelibrary

import org.json.JSONException
import org.json.JSONObject




object FormatJson {
    fun format(strJson: String): String {
        // 计数tab的个数
        var tabNum = 0
        val jsonFormat = StringBuffer()
        val length = strJson.length
        var last = 0.toChar()
        for (i in 0 until length) {
            val c = strJson[i]
            if (c == '{') {
                tabNum++
                jsonFormat.append(
                    """
                        $c
                        
                        """.trimIndent()
                )
                jsonFormat.append(FormatJson.getSpaceOrTab(tabNum))
            } else if (c == '}') {
                tabNum--
                jsonFormat.append("\n")
                jsonFormat.append(FormatJson.getSpaceOrTab(tabNum))
                jsonFormat.append(c)
            } else if (c == ',') {
                jsonFormat.append(
                    """
                        $c
                        
                        """.trimIndent()
                )
                jsonFormat.append(FormatJson.getSpaceOrTab(tabNum))
            } else if (c == ':') {
                jsonFormat.append("$c ")
            } else if (c == '[') {
                tabNum++
                val next = strJson[i + 1]
                if (next == ']') {
                    jsonFormat.append(c)
                } else {
                    jsonFormat.append(
                        """
                            $c
                            
                            """.trimIndent()
                    )
                    jsonFormat.append(FormatJson.getSpaceOrTab(tabNum))
                }
            } else if (c == ']') {
                tabNum--
                if (last == '[') {
                    jsonFormat.append(c)
                } else {
                    jsonFormat.append(
                        """
                            
                            ${FormatJson.getSpaceOrTab(tabNum)}$c
                            """.trimIndent()
                    )
                }
            } else {
                jsonFormat.append(c)
            }
            last = c
        }
        return jsonFormat.toString()
    }

    private fun getSpaceOrTab(tabNum: Int): String {
        val sbTab = StringBuffer()
        for (i in 0 until tabNum) {
            sbTab.append('\t')
        }
        return sbTab.toString()
    }

    fun getMap(jsonString: String?): Map<String, Any>? {
        val jsonObject: JSONObject
        try {
            jsonObject = JSONObject(jsonString)
            val keyIter = jsonObject.keys()
            var key: String
            var value: Any
            val valueMap: MutableMap<String, Any> = HashMap()
            while (keyIter.hasNext()) {
                key = keyIter.next() as String
                value = jsonObject[key]
                valueMap[key] = value
            }
            return valueMap
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }


}