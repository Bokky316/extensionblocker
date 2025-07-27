// src/components/common/FormErrorMessage.tsx
import { AlertCircle } from 'lucide-react'

interface FormErrorMessageProps {
  message?: string
}

export const FormErrorMessage = ({ message }: FormErrorMessageProps) => {
  if (!message) return null

  return (
    <p className="flex items-center gap-1 text-red-500 text-xs mt-1 leading-snug">
      <AlertCircle size={14} strokeWidth={2} className="shrink-0 min-w-[14px]" />
      <span>{message}</span>
    </p>
  )
}
