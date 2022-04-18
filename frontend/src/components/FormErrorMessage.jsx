import { useEffect, useState } from "react"
import If from "./If"

const FormErrorMessage = ({ message }) => {
  const [hasError, setHasError] = useState(message?.length > 0)

  useEffect(() => {
    setHasError(message?.length > 0)
  }, [message])

  return (
    <If condition={hasError}>
      <p className="error">{message}</p>
    </If>
  )
}

export default FormErrorMessage