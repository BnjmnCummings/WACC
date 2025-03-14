package wacc

import parsley.errors.ErrorBuilder

abstract class MyErrorBuilder extends ErrorBuilder[Err] {
    override def build(
        pos: (Int, Int), 
        source: Option[String],
        // build is only used for syntax errors
        lines: ErrorLines): Err = Err(source, pos, lines, ErrorType.SyntaxError)

    override def vanillaError(
        unexpected: Option[ErrorItem],
        expected: Set[ErrorItem],
        reasons: Set[String],
        line: String
    ): ErrorLines = VanillaError(unexpected, expected, reasons, line)

    override def specializedError(
        msgs: Set[String],
        line: String
      ): ErrorLines = SpecializedError(msgs, line)

    override def pos(line: Int, col: Int): (Int, Int) = (line, col)
    override def source(sourceName: Option[String]): Option[String] = sourceName
    override def combineExpectedItems(alts: Set[ErrorItem]): Set[ErrorItem] = alts
    override def combineMessages(alts: Seq[String]): Set[String] = alts.toSet
    override def unexpected(item: Option[ErrorItem]): Option[ErrorItem] = item
    override def expected(alts: Set[ErrorItem]): Set[ErrorItem] = alts
    override def message(msg: String): String = msg
    override def reason(msg: String): String = msg
    override def raw(item: String): RawItem = RawItem(item)
    override def named(item: String): NamedItem = NamedItem(item)
    val endOfInput: EndOfInputItem.type = EndOfInputItem

    val numLinesAfter: Int = 1
    val numLinesBefore: Int = 1
    override def lineInfo(
        line: String,
        linesBefore: Seq[String],
        linesAfter: Seq[String],
        lineNum: Int, errorPointsAt: Int, errorWidth: Int
        ): String = genErrorMessageCodeBlock(
            line = line, 
            linesBefore = linesBefore, 
            linesAfter = linesAfter,
            errorPointsAt = errorPointsAt,
            errorWidth = errorWidth
        )

    type Position = (Int, Int)
    type Source = Option[String]
    type ErrorInfoLines = ErrorLines
    type Item = ErrorItem
    type Raw = RawItem
    type Named = NamedItem
    type EndOfInput = EndOfInputItem.type
    type Message = String
    type Messages = Set[String]
    type ExpectedItems = Set[ErrorItem]
    type ExpectedLine = Set[ErrorItem]
    type UnexpectedLine = Option[ErrorItem]
    type LineInfo = String
}

