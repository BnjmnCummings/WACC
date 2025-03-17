package wacc.semantic

import wacc.*
import wacc.KnownType.Pair

enum Constraint {
    case Is(refTy: SemType)
    case IsExactly(refTy: SemType)
    case IsNumeric
    case IsNumericNoError
    case IsCharacter
    case IsNumericOrCharacter
    case IsBoolean
    case IsExitable
    case IsFreeable
    case IsString
    case IsReadable
    case IsPairNoError
}

object Constraint {
    val Unconstrained = Is(?) // Always passes
    val IsArray = Is(KnownType.Array(?))
    val IsPair = Is(KnownType.Pair(?, ?))
}

extension (ty: SemType) def satisfies (c: Constraint)(using ctx: TypeCheckerCtx): Option[SemType] = (ty, c) match 
    case (Pair(?, ?), Constraint.Is(KnownType.Pair(_, _))) => Some(Pair(?, ?))
    case (ty, Constraint.Is(refTy)) => (ty ~ refTy).orElse {
        ctx.error(TypeMismatch(ty, refTy))
    }
    case (ty, Constraint.IsExactly(refTy)) => if (ty == refTy) then Some(ty) else None 
    case (?, Constraint.IsReadable) => ctx.error(NonReadableType(?))
    case (?, _) => Some(?)
    case (kty@KnownType.Int, Constraint.IsNumeric) => Some(kty)
    case (kty, Constraint.IsNumeric) => ctx.error(NonNumericType(kty))
    case (kty@KnownType.Int, Constraint.IsNumericNoError) => Some(kty)
    case (kty, Constraint.IsNumericNoError) => None
    case (kty@KnownType.Char, Constraint.IsCharacter) => Some(kty)
    case (kty, Constraint.IsCharacter) => ctx.error(NonCharacterType(kty))
    case (kty@KnownType.Int, Constraint.IsNumericOrCharacter) => Some(kty)
    case (kty@KnownType.Char, Constraint.IsNumericOrCharacter) => Some(kty)
    case (kty, Constraint.IsNumericOrCharacter) => ctx.error(NonNumericType(kty))
    case (kty@KnownType.Boolean, Constraint.IsBoolean) => Some(kty)
    case (kty, Constraint.IsBoolean) => ctx.error(NonBooleanType(kty))
    case (kty@KnownType.String, Constraint.IsString) => Some(kty)
    case (kty, Constraint.IsString) => ctx.error(NonStringType(kty))
    case (kty@KnownType.Int, Constraint.IsExitable) => Some(kty)
    case (kty, Constraint.IsExitable) => ctx.error(NonExitableType(kty))
    case (kty@(KnownType.Array(_) | KnownType.Pair(_, _)), Constraint.IsFreeable) => Some(kty)
    case (kty, Constraint.IsFreeable) => ctx.error(NonFreeableType(kty))
    case (kty@(KnownType.Int | KnownType.Char), Constraint.IsReadable) => Some(kty)
    case (kty, Constraint.IsReadable) => ctx.error(NonReadableType(kty))
    case (kty@KnownType.Pair(_, _), Constraint.IsPairNoError) => Some(kty)
    case (kty, Constraint.IsPairNoError) => None
