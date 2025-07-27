interface FormInputProps {
  value: string
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
  onBlur?: () => void
  placeholder?: string
  error?: string
  maxLength?: number
  className?: string
}

export const FormInput = ({
  value,
  onChange,
  onBlur,
  placeholder,
  error,
  maxLength = 20,
  className = '',
}: FormInputProps) => {
  return (
    <input
      value={value}
      onChange={onChange}
      onBlur={onBlur}
      placeholder={placeholder}
      maxLength={maxLength}
      className={`w-[240px] md:w-[280px] px-4 py-3 text-sm leading-[20px] rounded-md border
        placeholder:text-sm placeholder:leading-[20px] placeholder:text-gray-400
        ${error ? 'border-red-500' : 'border-gray-300'}
        ${className}`}
    />
  )
}
