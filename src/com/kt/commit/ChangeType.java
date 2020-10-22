package com.kt.commit;

public enum ChangeType {

    FEAT("Features", "Добавление нового функционала"),
    FIX("Bug Fixes", "Исправление ошибок"),
    DOCS("Documentation", "Обновление документации"),
    STYLE("Styles", "Правки по кодстaйлу (табы, отступы, точки, запятые и т.д.)"),
    REFACTOR("Code Refactoring", "Правки кода без исправления ошибок или добавления новых функций"),
    PERF("Performance Improvements", "Изменения направленные на улучшение производительности"),
    TEST("Tests", "Добавление тестов"),
    BUILD("Builds", "Сборка проекта или изменения внешних зависимостей"),
    CI("Continuous Integrations", "Настройка CI и работа со скриптами"),
    CHORE("Chores", "Прочие изменения не src или test files"),
    REVERT("Reverts", "Откат на предыдущие коммиты");

    public final String title;
    public final String description;

    ChangeType(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String label() {
        return this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.label(), this.description);
    }
}
