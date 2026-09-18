package eu.vctrl4.business.datasource.storage.entities

abstract class IdName
{
    abstract var Id:String
    abstract var Name:String?
    abstract var IsDeleted: Boolean?
    abstract var isSelectedOne:Boolean
}