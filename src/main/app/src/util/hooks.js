import { useState } from "react";

const useBooleanState = initialValue => {
  const [state, setState] = useState(!!initialValue);
  return [state, () => setState(true), () => setState(false)];
};

export { useBooleanState };
export default useBooleanState;
