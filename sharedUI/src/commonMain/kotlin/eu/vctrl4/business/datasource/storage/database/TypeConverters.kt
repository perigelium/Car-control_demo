package eu.vctrl4.storage.database

import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.serialization.json.*

class TypeConverters {

	/*    @TypeConverter
		fun stringToListOfStrings(data: String?): List<String>?
		{
			if (data == null)
			{
				return emptyList()
			}
			val listType = object : TypeToken<List<String?>?>()
			{}.type
			return gson.fromJson(data, listType)
		}

		@TypeConverter
		fun ListOfStringsToString(someObjects: List<String?>?): String?
		{
			return gson.toJson(someObjects)
		}*/

	/*    @TypeConverter
		fun fromImages(fileInfo: List<FileInfo?>?): String?
		{
			if (fileInfo == null)
			{
				return null
			}
			val type: Type = object : TypeToken<List<FileInfo?>?>()
			{}.type
			return gson.toJson(fileInfo, type)
		}

		@TypeConverter
		fun toImages(fileInfoString: String?): List<FileInfo>?
		{
			if (fileInfoString == null)
			{
				return null
			}
			val type: Type = object : TypeToken<List<FileInfo?>?>()
			{}.type
			return gson.fromJson<List<FileInfo>>(fileInfoString, type)
		}*/

	@TypeConverter
	fun fromDepartments(objects: List<Department>?): String? {
		if (objects == null) {
			return null
		}
		return objects.let { Json.encodeToString(it) }
	}

	@TypeConverter
	fun toDepartments(strObjects: String?): List<Department>? {
		if (strObjects == null) {
			return null
		}
		return strObjects.let { Json.decodeFromString(it) }
	}

	@TypeConverter
	fun fromIdNameSimple(objects: List<IdNameValueName>?): String? {
		if (objects == null) {
			return null
		}
		return objects.let { Json.encodeToString(it) }
	}

	@TypeConverter
	fun toIdNameSimple(strObjects: String?): List<IdNameValueName>? {
		if (strObjects == null) {
			return null
		}
		return strObjects.let { Json.decodeFromString(it) }
	}

	/*    @TypeConverter
		fun toMapStringInt(value: String): Map<String?, Int?> {
			val mapType = object : TypeToken<Map<String?, Int?>>() {}.type
			return Gson().fromJson(value, mapType)
		}

		@TypeConverter
		fun fromMapStringInt(map: Map<String?, Int?>): String {
			val gson = Gson()
			return gson.toJson(map)
		}*/
/*	@TypeConverter
	fun fromIdNameValueName(objects: List<IdNameValueName>?): String? {
		if (objects == null) {
			return null
		}
		return objects.let { Json.encodeToString(it) }
	}

	@TypeConverter
	fun toIdNameValueName(strObjects: String?): List<IdNameValueName>? {
		if (strObjects == null) {
			return null
		}
		return strObjects.let { Json.decodeFromString(it) }
	}*/
}