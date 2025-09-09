package org.gurikin.postgresblog.audit

enum class OperationType(
    private val code: Char
) {
    DELETE('D'),
    INSERT('I'),
    UPDATE('U');

    companion object {
        fun findByLetter(char: Char): OperationType {
            return entries.stream().filter { it.code == char }.findFirst().orElseThrow()
        }
    }
}
