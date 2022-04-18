import { useEffect, useState } from "react"

const FormErrorMessage = ({ message }) => {
  const [hasError, setHasError] = useState(message?.length > 0)

  useEffect(() => {
    setHasError(message?.length > 0)
  }, [message])

  return (
    <>
      {
        hasError &&
        <p className="error">{message}</p>
      }
    </>
  )
}

export default FormErrorMessage