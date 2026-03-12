package io.moxd.multiband.model.screen

import io.moxd.multiband.model.persistence.studyContactGroups

object UIElementSizes {
    var screenHeight = 0
    var contactHeight = 0
        set(value) {
            if (field != value && value != 0) {
                field = value
                updateItemGroups()
            }
        }
    var contactDividerHeight = 0
        set(value) {
            if (field != value && value != 0) {
                field = value
                updateItemGroups()
            }
        }
    var groupSpacerHeight = 0
        set(value) {
            if (field != value && value != 0) {
                field = value
                updateItemGroups()
            }
        }
    var groupTitleHeight = 0
        set(value) {
            if (field != value && value != 0) {
                field = value
                updateItemGroups()
            }
        }
    var studyItemGroups: List<ItemGroup> = emptyList()
        private set

    private fun updateItemGroups() {
        var prevItemGroupEnd = 0F
        studyItemGroups = studyContactGroups.map {
            val groupHeight =
                groupTitleHeight + 2 * 20 /*Card padding*/ + it.contacts.size * contactHeight + (it.contacts.size - 1) * contactDividerHeight + groupSpacerHeight
            val newItemGroup = ItemGroup(groupHeight.toFloat(), 100f)
            newItemGroup.setVerticalStartPosition(prevItemGroupEnd)
            prevItemGroupEnd = newItemGroup.verticalSpan.endPixel
            newItemGroup
        }
    }
}