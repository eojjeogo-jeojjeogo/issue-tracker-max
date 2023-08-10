import { useEffect, MutableRefObject } from "react";

export function useOutsideClick(
  ref: MutableRefObject<HTMLElement | null>,
  exceptionRefs: MutableRefObject<HTMLElement | null>[],
  callback: () => void
): void {
  useEffect(() => {
    function handleClickOutside(event: MouseEvent): void {
      if (
        ref.current &&
        !ref.current.contains(event.target as Node) &&
        !exceptionRefs.some(
          (exceptionRef) => exceptionRef.current?.contains(event.target as Node)
        )
      ) {
        callback();
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [ref, exceptionRefs, callback]);
}
